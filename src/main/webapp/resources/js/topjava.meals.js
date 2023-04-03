const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "searching": false,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ]
        })
    );
});

function applyFilter() {
    $.get(mealAjaxUrl + "filter", $("#filter").serialize(), function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}

function clearFilter() {
    $("#filter").find(":input").val("");
    $.get(mealAjaxUrl, "", updateTable);
}
