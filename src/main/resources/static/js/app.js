$.fn.dblocPage = function(options) {
    var settings  = $.extend({

    });
};

var app = angular.module("dblocapp", ['ngMessages', 'angucomplete-alt']);
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
            var url = window.location.href + '/' + id;
            $http({
                method: 'GET',
                url : url
            }).success(function(data) {
                $scope.formData = data;
            });
        };
        
        $scope.deleteRecord = function(id, index) {
        	$('#are-you-sure').modal('show');
        };

        $scope.delete = function(id, index) {
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
            console.log(form);
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
 * Controller for Admin Order. Extends FormController
 */
app.controller("OrderController", function($scope, $http, $controller){
    angular.extend(this, $controller('FormController', {$scope:$scope}));
    $scope.formData.items = [];

    $scope.getMemberList = function(){
        $scope.memberList = [];
        var url = window.location.href + '/memberList';
        $http({
            method: 'GET',
            url: url,
            data: {'key' : $scope.memberName},
            headers: {'Content-Type': 'application/json'}
        }).success(function (data) {
            $scope.memberList.push(data.object);

        });
    }

    $scope.memberSelected = function(selected) {
        $scope.formData.sellerId = selected.originalObject.id;
    };

    $scope.productSelected = function(selected) {
        var items = $scope.formData.items;
        items[this.$parent.$index].productId = selected.originalObject.id;
        items[this.$parent.$index].productPrice = selected.originalObject.price;
        $scope.quantityChanged(this.$parent.$index);
    };

    $scope.quantityChanged = function(index) {
        var items = $scope.formData.items;
        if(items.length > 0) {
            var price = items[index].productPrice;
            if (price != null) {
                if (!$.isNumeric(items[index].quantity)) return;
                items[index].totalPrice = price * items[index].quantity;
                var sum = 0;
                $.each(items, function (i, val) {
                    sum += val.totalPrice
                });
                $scope.formData.totalPrice = sum;
            }
        } else {
            $scope.formData.totalPrice = 0;
        }
    }

    $scope.addItem = function() {
        $scope.formData.items.push({

        });
    };

    $scope.deleteItem = function(index) {
        $scope.formData.items.splice(index, 1);
        $scope.quantityChanged(index);
    };

});

/**
 * Controller for Product. Extends FormController
 */
app.controller("ProductController", function($scope, $http, $controller) {
    angular.extend(this, $controller('FormController', {$scope: $scope}));

    //Set default value to PERCENTAGE
    $scope.formData.memberPointsType = 'PERCENTAGE';

    $scope.setMemberPointsType = function (value) {
        $scope.formData.memberPointsType = value;

        $('button .active').removeClass('active');
    };
});

app.controller("MemberEarningsController", function($scope, $http) {
   $scope.init = function() {
       $scope.records = [];
       var url = window.location.href + '/members';
       $http({
           method: 'GET',
           url: url
       }).success(function (data) {
           $scope.records = data.members;
       });
   };
    $scope.markClaimed = function(index) {
      var record = $scope.records[index];
        if(record != null) {
            var url = window.location.href + '/markClaimed';
            $http({
                method: 'POST',
                url: url,
                data: {memberId : record.memberId, totalPoints : record.totalPoints, totalEarnings : 300 * record.totalPoints},
                headers: {'Content-Type': 'application/json'}
            }).success(function (data) {
                record.isClaimed = true;
            });
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