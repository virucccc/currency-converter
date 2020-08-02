function swapValute() {
    var pickup = $('#from').val();
    $('#from').val($('#to').val());
    $('#to').val(pickup);
}