$(function () {
    $(`.logout-btn`).click((e) =>{
        e.preventDefault();
        $(`#logout-form`).submit();
    });
});
