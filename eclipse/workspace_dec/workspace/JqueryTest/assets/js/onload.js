$(document).ready(function () {
    $("ul li:last-child").addClass('last');
    $(".colgroup .col:last-child").addClass('last');
    $(".applications figure:last-child").addClass('last');
    // Call those functions
    scrolltop();
});
// Top to magazine scroll
function scrolltop() {
    $('a.self').click(
function () {
    $.scrollTo('#page', 500);
    return false;
}
);
}
