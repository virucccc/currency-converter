package com.virucccc.converter.utils;

import com.virucccc.converter.model.Exchange;
import com.virucccc.converter.model.Valute;
import com.virucccc.converter.repository.ExchangeRepository;
import com.virucccc.converter.repository.ValuteRepository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.*;

public class ValutesExchange {
    private static final String url = "http://www.cbr.ru/scripts/XML_daily.asp";

    public static Map<Integer, Valute> getValutes() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(url).openStream());
        doc.getDocumentElement().normalize();
        Element root = doc.getDocumentElement();

        Map<Integer, Valute> map = new WeakHashMap<>();


        NodeList nodeList = root.getChildNodes();
        map.put(0, new Valute(1, "Российский рубль", "RUB", 1));
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList child = nodeList.item(i).getChildNodes();
            int id = Integer.parseInt(child.item(0).getTextContent());
            String shortname = child.item(1).getTextContent();
            String fullname = child.item(3).getTextContent();
            float value = Float.parseFloat(child.item(4).getTextContent().replace(",", "."));
            map.put(id, new Valute(id, fullname, shortname, value));
        }

        return map;
    }

    public static Calendar getDate() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(url).openStream());
        doc.getDocumentElement().normalize();
        String[] date = doc.getDocumentElement().getAttribute("Date").split("\\.");
        int[] ints = new int[date.length];
        int i = 0;
        for (String s : date) {
            ints[i++] = Integer.parseInt(s);
        }
        return new GregorianCalendar(ints[2], ints[1] - 1, ints[0]);
    }

    public static List<Valute> createValute(ValuteRepository repository, Collection<Valute> valuteList) {
        List<Valute> valutes = new ArrayList<>();
        try {
            Map<Integer, Valute> map = ValutesExchange.getValutes();
            Collection<Valute> valuteCollection = map.values();
            valutes.addAll(valuteCollection);
            valuteCollection.removeAll(valuteList);

            for (Valute v : valuteCollection) {
                repository.save(v);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

        return valutes;
    }

    public static void createExchanges(ExchangeRepository repository, Collection<Valute> valuteList) {
        try {
            Calendar calendar = ValutesExchange.getDate();
            List<Exchange> exchangeList = repository.findAllByDate(calendar);
            for (Valute v : valuteList) {
                Exchange exchange = new Exchange(v.getValue(), v, calendar);
                if (!exchangeList.contains(exchange)) {
                    repository.save(exchange);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static Valute findValuteByShortname(Collection<Valute> valutes, String shortname) {
        for (Valute v : valutes) {
            if (v.getShortname().equals(shortname)) {
                return v;
            }
        }
        return null;
    }
}
