angular.module('app')
.factory("facebookService", ["$resource",  function ($resource) {
    var resource = $resource("/perfil/", null,
        {
            getData:
                {
                    method:'GET',
                    url: '/profile/data',
                },
            getProfilePhoto:
                {
                    method:'GET',
                    url: '/profile/photo',
                    isArray: false,
                },
            getAlbums:
                {
                    method:'GET',
                    url: '/profile/albums',
                    isArray: true,
                },
            getAlbumPhotos:
                {
                    method:'GET',
                    url: '/albums/photos',
                    isArray: true,
                },
        });

    var service = {
        getData: function () {
            return resource.getData().$promise;
        },
        getProfilePhoto: function () {
            return resource.getProfilePhoto().$promise;
        },
        getAlbums: function () {
            return resource.getAlbums().$promise;
        },
        getAlbumPhotos: function (albumId) {
            return resource.getAlbumPhotos({'album-id': albumId}).$promise;
        }
    }
    return service;
}]);