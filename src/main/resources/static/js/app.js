$.fn.dblocPage = function(options) {
    var settings  = $.extend({

    });
};

var app = angular.module("dblocapp", ['ngMessages']);
/*
* Controllers go here...
* */
app.
    controller("FormController", function($scope,$http) {
        $scope.formData = {};
        $scope.records = [];

        $scope.init = function() {
            var url = window.location.href + '/list';
            $http({
                method: 'GET',
                url : url
            }).success(function(data) {
                $scope.records = data;
            });
        };

        $scope.get = function(id) {
            console.log(id);
            var url = window.location.href + '/' + id;
            $http({
                method: 'GET',
                url : url
            }).success(function(data) {
                $scope.formData = data;
            });
        };

        $scope.delete = function(id, index) {
            console.log(id);
            var url = window.location.href + '/' + id;
            $http({
                method: 'DELETE',
                url : url
            }).success(function(data) {
                console.log(data);
                $scope.records.splice(index, 1);
            });
        };

        $scope.submit = function(form, modal) {
            $scope.submitted = true;
            if(!form.$valid) {

            } else {
                if($scope.formData.id) {
                    var url = window.location.href + '/' + $scope.formData.id;
                    $http({
                        method: 'PUT',
                        url: url,
                        data: $scope.formData,
                        headers: {'Content-Type': 'application/json'}
                    }).success(function (data) {
                        $('#form-body').modal('hide');
                    });
                } else {
                    var url = window.location.href + '/create';
                    $http({
                        method: 'POST',
                        url: url,
                        data: $scope.formData,
                        headers: {'Content-Type': 'application/json'}
                    }).success(function (data) {
                        $scope.records.push(data.object);
                        $('#form-body').modal('hide');
                    });
                }
            }
        };
    });

/**
 * Custom directives...
 */
//Directive for number only input... Weird error...
app.directive('numberOnlyInput', function () {
    return {
        restrict: 'EA',
        template: '<input name="{{inputName}}" ng-model="inputValue" class="{{class}}" />',
        scope: {
            inputValue: '=',
            inputName: '='
        },
        link: function (scope) {
            scope.$watch('inputValue', function(newValue,oldValue) {
                var arr = String(newValue).split("");
                if (arr.length === 0) return;
                if (arr.length === 1 && (arr[0] == '-' || arr[0] === '.' )) return;
                if (arr.length === 2 && newValue === '-.') return;
                if (isNaN(newValue)) {
                    scope.inputValue = oldValue;
                }
            });
        }
    };
});