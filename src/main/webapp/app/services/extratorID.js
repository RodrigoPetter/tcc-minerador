angular.module('app')
    .factory("extratorID", function ($resource) {
        return service = {
            extrair: function (obj) {

                var url = obj._links.self.href;
                var id = url.slice(url.lastIndexOf("/")+1);
                console.log("ID: "+id+" self:"+obj._links.self.href);
                return id;
            }
        };
    });