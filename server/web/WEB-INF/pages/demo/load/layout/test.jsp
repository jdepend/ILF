<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/common/taglibs.jsp"%>

<script type="text/javascript">

/*!
 * Ext JS Library 3.2.0
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.onReady(function() {
    var structure = {
        Asia: ['Beijing', 'Tokyo'],
        Europe: ['Berlin', 'London', 'Paris']
    },
    products = ['ProductX', 'ProductY'],
    fields = [],
    columns = [],
    data = [],
    
    topRow = [],
    continentGroupRow = [],
    cityGroupRow = [];
    
    
    topRow = [{header: 'top', colspan: 10, align: 'center'}];
    
      continentGroupRow =
      [
          {header: 'Asia', colspan: 4, align: 'center'},
          {header: 'Europe', colspan: 6, align: 'center'}
      ]
      
      cityGroupRow =
      [
          {header: 'Beijing', colspan: 2, align: 'center'},
          {header: 'Tokyo', colspan: 2, align: 'center'},
          {header: 'Berlin', colspan: 2, align: 'center'},
          {header: 'London', colspan: 2, align: 'center'},
          {header: 'Paris', colspan: 2, align: 'center'}
      ]

    var group = new Ext.ux.grid.ColumnHeaderGroup({
        rows: [topRow, continentGroupRow, cityGroupRow]
    });
    
    
      fields =
      [
        {type: 'int', name: 'BeijingProductX'},
        {type: 'int', name: 'BeijingProductY'},
        {type: 'int', name: 'TokyoProductX'},
        {type: 'int', name: 'TokyoProductY'},
        {type: 'int', name: 'BerlinProductX'},
        {type: 'int', name: 'BerlinProductY'},
        {type: 'int', name: 'LondonProductX'},
        {type: 'int', name: 'LondonProductY'},
        {type: 'int', name: 'ParisProductX'},
        {type: 'int', name: 'ParisProductY'}
      ]

      columns =
      [
         {dataIndex: 'BeijingProductX', header: 'ProductX'},
         {dataIndex: 'BeijingProductY', header: 'ProductY'},
         {dataIndex: 'TokyoProductX', header: 'ProductX'},
         {dataIndex: 'TokyoProductY', header: 'ProductY'},
         {dataIndex: 'BerlinProductX', header: 'ProductX'},
         {dataIndex: 'BerlinProductY', header: 'ProductY'},
         {dataIndex: 'LondonProductX', header: 'ProductX'},
         {dataIndex: 'LondonProductY', header: 'ProductY'},
         {dataIndex: 'ParisProductX', header: 'ProductX'},
         {dataIndex: 'ParisProductY', header: 'ProductY'}
      ]

    var grid = new Ext.grid.GridPanel({
        renderTo: 'column-group-grid',
        title: 'Sales By Location',
        width: 1000,
        height: 400,
        store: new Ext.data.ArrayStore({
            fields: fields,
            data: data
        }),
        columns: columns,
        viewConfig: {
            forceFit: true
        },
        plugins: group
    });
});
</script>

<div id='column-group-grid'></div>