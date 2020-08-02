package com.virucccc.converter.controller;

import com.virucccc.converter.model.*;
import com.virucccc.converter.repository.ExchangeRepository;
import com.virucccc.converter.repository.HistoryRepository;
import com.virucccc.converter.repository.ValuteRepository;
import com.virucccc.converter.utils.ValutesExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.virucccc.converter.utils.ValutesExchange.findValuteByShortname;
import static com.virucccc.converter.utils.ValutesExchange.getValutes;

@Controller
public class ConverterController {
    @Autowired
    private ValuteRepository valuteRepository;
    @Autowired
    private ExchangeRepository exchangeRepository;
    @Autowired
    private HistoryRepository historyRepository;

    private final String valuteFrom = "RUB";
    private final String valuteTo = "USD";
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/converter")
    public String converter(@AuthenticationPrincipal User user,
                            @RequestParam(name = "from", defaultValue = valuteFrom) String from,
                            @RequestParam(name = "to", defaultValue = valuteTo) String to,
                            Model model) {
        Collection<Valute> valutes = valuteRepository.findAll();
        Collection<History> histories = historyRepository.findByUser(user);
        model.addAttribute("valutes", valutes);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("histories", histories);
        return "converter";
    }

    @PostMapping("/converter")
    public String convert(@AuthenticationPrincipal User user,
                          @RequestParam(name = "from", defaultValue = valuteFrom) String from,
                          @RequestParam(name = "to", defaultValue = valuteTo) String to,
                          @RequestParam(name = "val", defaultValue = "1") BigDecimal fromValue,
                          Model model) {
        Calendar date = null;
        try {
            date = ValutesExchange.getDate();
        } catch (Exception e) {
            e.getStackTrace();
        }

        Collection<Valute> valutes = valuteRepository.findAll();
        valutes = ValutesExchange.createValute(valuteRepository, valutes);
        ValutesExchange.createExchanges(exchangeRepository, valutes);
        Collection<History> histories = historyRepository.findByUser(user);
        Valute fromValute = findValuteByShortname(valutes, from);
        Valute toValute = findValuteByShortname(valutes, to);

        model.addAttribute("valutes", valutes);
        model.addAttribute("histories", histories);

        if (fromValute == null || toValute == null) {
            return "converter";
        }

        Exchange fromExchange = exchangeRepository.findByIdDate(fromValute.getId(), date);
        Exchange toExchange = exchangeRepository.findByIdDate(toValute.getId(), date);
        float ratio = fromExchange.getValue() / toExchange.getValue();
        BigDecimal toValue = round(fromValue.multiply(new BigDecimal(ratio)));
        fromValue = round(fromValue);

        History history = historyRepository.contains(user, fromExchange, toExchange, fromValue.floatValue(),
                toValue.floatValue());
        if (history == null) {
            history = new History(user, fromExchange, toExchange, fromValue.floatValue(),
                    toValue.floatValue());
            histories.add(history);
            historyRepository.save(history);
        }

        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("fromValUpdate", fromValue);
        model.addAttribute("toValUpdate", toValue);

        return "converter";
    }

    private BigDecimal round(BigDecimal number) {
        return number.setScale(2, RoundingMode.HALF_EVEN);
    }

    @GetMapping(value = "/converter", params = "datepicker")
    public String converter(@AuthenticationPrincipal User user,
                            @RequestParam(name = "datepicker") String date,
                            Model model) {
        Collection<HistoryExchange> histories = new ArrayList<>();
        try {
            Calendar cal = new GregorianCalendar();
            if (date.isEmpty()) {
                date = sdf.format(cal.getTime());
            }

            Date dates = sdf.parse(date);
            cal.setTime(dates);
            histories.addAll(historyRepository.findByUserDate(user, cal));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        model.addAttribute("histories", histories);
        Collection<Valute> valutes = valuteRepository.findAll();
        model.addAttribute("valutes", valutes);
        model.addAttribute("from", valuteFrom);
        model.addAttribute("to", valuteTo);

        return "converter";
    }
}
