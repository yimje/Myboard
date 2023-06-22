let index = {
    init: function () {
        $("#btn-update").on("click", () => {
            this.update();
        });
    },

    update: function () {
        let data = {
            role: $(".radio-value:checked").val(),
        };
        let id = $("#id").val();

        $.ajax({
            type: "PUT",
            url: "/admin/manage/member/edit/" + id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            alert("회원 수정 완료!🎉")
            location.href = "/admin/manage/member";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
}

index.init();