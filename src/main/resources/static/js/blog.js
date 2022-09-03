var category = {
    init : function () {
        var _this = this;
        $('#btn-category-save').on('click', function () {
            _this.save();
        });

        $('#btn-category-update').on('click', function () {
            _this.update();
        });

        $('#btn-category-delete').on('click', function () {
            _this.delete();
        });
    },

    save : function () {
        var data = {
            name: $('#name').val(),
            topic: $('#topic').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/categories',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            window.location.href = '/categories/setting';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
                   name: $('#name').val(),
                   topic: $('#topic').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/categories/'+id,
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            window.location.href = '/categories/setting';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/categories/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            window.location.href = '/categories/setting';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

var post = {
    init : function () {
        var _this = this;
        $('#btn-post-save').on('click', function () {
            _this.save();
        });

        $('#btn-post-update').on('click', function () {
            _this.update();
        });

        $('#btn-post-delete').on('click', function () {
            _this.delete();
        });
    },

    save : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val(),
            category: $('#category').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            window.location.href = '/blog';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
                    title: $('#title').val(),
                    content: $('#content').val(),
                    category: $('#category').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            window.location.href = '/posts/'+id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            window.location.href = '/blog';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

post.init();
category.init();