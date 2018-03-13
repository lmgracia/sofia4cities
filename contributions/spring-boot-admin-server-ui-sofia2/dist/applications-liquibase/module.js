webpackJsonp([11],{0:function(t,n,r){(function(t){"use strict";var n=r(1),e=n.module("sba-applications-liquibase",["sba-applications"]);t.sbaModules.push(e.name),e.controller("liquibaseCtrl",r(109)),e.component("sbaLiquibaseChangeLogEntry",r(108)),e.config(["$stateProvider",function(t){t.state("applications.liquibase",{url:"/liquibase",templateUrl:"applications-liquibase/views/liquibase.html",controller:"liquibaseCtrl"})}]),e.run(["ApplicationViews","$http","$sce",function(t,n,r){t.register({order:100,title:r.trustAsHtml('<i class="fa fa-database fa-fw"></i>Liquibase'),state:"applications.liquibase",show:function(t){return n.head("api/applications/"+t.id+"/liquibase").then(function(){return!0}).catch(function(){return!1})}})}])}).call(n,function(){return this}())},108:function(t,n,r){"use strict";t.exports={bindings:{entry:"<changeLogEntry"},controller:function(){var t=this;t.getExecutionClass=function(){switch(t.entry.EXECTYPE){case"EXECUTED":return"label-success";case"FAILED":return"label-important";case"SKIPPED":return"";case"RAN":case"MARK_RAN":return"label-warning";default:return"label-info"}}},template:r(157)}},109:function(t,n){"use strict";t.exports=["$scope","$http","application",function(t,n,r){"ngInject";t.reports=[],t.searchFilter=null,t.refresh=function(){n.get("api/applications/"+r.id+"/liquibase").then(function(n){Array.isArray(n.data)&&(0===n.data.length||n.data[0].hasOwnProperty("name"))?t.reports=n.data:t.reports=[{name:"liqibase",changeLogs:n.data}]})},t.refresh()}]},157:function(t,n){t.exports='<sba-info-panel panel-title="{{$ctrl.entry.ID}}" category="{{$ctrl.entry.FILENAME.split(\'/\').pop()}}">\n  <table class="table">\n    <tr>\n      <td>Id</td>\n      <td ng-bind="$ctrl.entry.ID"></td>\n    </tr>\n    <tr>\n      <td>Author</td>\n      <td ng-bind="$ctrl.entry.AUTHOR"></td>\n    </tr>\n    <tr>\n      <td>File</td>\n      <td ng-bind="$ctrl.entry.FILENAME"></td>\n    </tr>\n    <tr>\n      <td>Execution Date</td>\n      <td ng-bind="$ctrl.entry.DATEEXECUTED | date:\'dd.MM.yyyy HH:mm:ss.sss\'"></td>\n    </tr>\n    <tr>\n      <td>Execution Order</td>\n      <td ng-bind="$ctrl.entry.ORDEREXECUTED"></td>\n    </tr>\n    <tr>\n      <td>Execution</td>\n      <td><span class="label {{$ctrl.getExecutionClass()}}" ng-bind="$ctrl.entry.EXECTYPE"></span></td>\n    </tr>\n    <tr>\n      <td>Checksum</td>\n      <td ng-bind="$ctrl.entry.MD5SUM"></td>\n    </tr>\n    <tr>\n      <td>Description</td>\n      <td ng-bind="$ctrl.entry.DESCRIPTION"></td>\n    </tr>\n    <tr ng-if="$ctrl.entry.COMMENTS">\n      <td>Comments</td>\n      <td ng-bind="$ctrl.entry.COMMENTS"></td>\n    </tr>\n    <tr>\n      <td>Deployment Id</td>\n      <td ng-bind="$ctrl.entry.DEPLOYMENT_ID"></td>\n    </tr>\n    <tr>\n      <td>Tag</td>\n      <td ng-bind="$ctrl.entry.TAG || \'-\'"></td>\n    </tr>\n    <tr ng-if="$ctrl.entry.CONTEXTS">\n      <td>Contexts</td>\n      <td ng-bind="$ctrl.entry.CONTEXTS"></td>\n    </tr>\n    <tr ng-if="$ctrl.entry.LABELS">\n      <td>Labels</td>\n      <td>\n        <span ng-repeat="label in $ctrl.entry.LABELS.split(\',\')"><span class="label" ng-bind="label"></span></span>\n      </td>\n    </tr>\n    <tr>\n      <td>Liquibase Version</td>\n      <td ng-bind="$ctrl.entry.LIQUIBASE"></td>\n    </tr>\n  </table>\n</sba-info-panel>'}});