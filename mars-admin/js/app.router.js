/**
 * Created by lixl on 2017/7/7.
 */
'use strict';

/**
 * Config for the router
 */
angular.module('app')
    .run(
        ['$rootScope', '$state', '$stateParams', '$location', '$cookieStore',
            function ($rootScope,   $state,   $stateParams,$location, $cookieStore) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
                $rootScope.$on('$stateChangeStart',function(event, toState, toParams, fromState, fromParams){
                    
                })
            }
        ]
    )
    .config(
        ['$stateProvider', '$urlRouterProvider',
            function ($stateProvider,   $urlRouterProvider) {
                $urlRouterProvider
                    .otherwise('/login');
                $stateProvider
                    .state('sys', {
                        abstract: true,
                        url: '/sys',
                        templateUrl: 'tpl/app.html'
                    })
                    .state('sys.dashboard', {
                        url: '/dashboard',
                        templateUrl: 'tpl/system/dashboard.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load([
                                        'vendor/echarts/echarts.common.min.js',
                                        'js/controllers/system/dashboard.js']);
                                }]
                        }
                    })
                    .state('login', {
                        url: '/login',
                        templateUrl: 'tpl/system/login.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load( ['js/controllers/system/login_controller.js']);
                                }]
                        }
                    })
                    .state('reset', {
                        url: '/reset',
                        templateUrl: 'tpl/system/reset.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load( ['js/controllers/system/reset_controller.js']);
                                }]
                        }
                    })
                    .state('system',{
                        url: '/system',
                        templateUrl: 'tpl/system/app.html'
                    })
                    .state('system.userlist', {
                        url: '/userlist',
                        templateUrl: 'tpl/system/user.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load( ['js/services/system.js','js/controllers/system/user_controller.js',
                                        'vendor/libs/moment.min.js']);
                                }]
                        }
                    })
                    .state('system.orglist', {
                        url: '/orglist',
                        templateUrl: 'tpl/system/org.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load(['js/controllers/system/org_controller.js']);
                                }]
                        }
                    })
                    .state('system.config', {
                        url: '/parameter',
                        templateUrl: 'tpl/system/config.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load(['js/controllers/system/config_controller.js']);
                                }]
                        }
                    })
                    .state('system.logger', {
                        url: '/logger',
                        templateUrl: 'tpl/system/logger.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load(['js/controllers/system/logger_controller.js']);
                                }]
                        }
                    })
                    .state('info', {
                        url: '/info',
                        templateUrl: 'tpl/system/app.html',
                        abstract: true
                    })
                    .state('info.list', {
                        url: '/list',
                        templateUrl: 'tpl/user/info_list.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load(['js/controllers/user/info_controller.js']);
                                }]
                        }
                    })
                    .state('info.notice', {
                        url: '/view',
                        templateUrl: 'tpl/user/notice.html',
                        resolve: {
                            deps: ['$ocLazyLoad',
                                function( $ocLazyLoad ){
                                    return $ocLazyLoad.load(['js/controllers/user/notice_controller.js']);
                                }]
                        }
                    })
            }
        ]
    )

.directive('onFinishRender', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function () {
                    scope.$emit('ngRepeatFinished');
                });
            }
        }
    }});
