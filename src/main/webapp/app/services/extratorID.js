angular.module('app')
    .factory("extratorID", function ($resource) {
        return service = {
            extrair: function (obj) {
                var url = obj._links.self.href;
                return url.slice(url.lastIndexOf("/")+1);
            }
        };
    });