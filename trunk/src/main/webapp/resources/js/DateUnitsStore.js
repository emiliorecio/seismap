/*
 * File: DateUnitsStore.js
 * Date: Tue Apr 12 2011 20:56:23 GMT-0300 (Argentina Standard Time)
 * 
 * This file was generated by Ext Designer version 1.1.2.
 * http://www.sencha.com/products/designer/
 *
 * This file will be auto-generated each and everytime you export.
 *
 * Do NOT hand edit this file.
 */

DateUnitsStore = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        DateUnitsStore.superclass.constructor.call(this, Ext.apply({
            storeId: 'dateUnitsStore',
            fields: [
                {
                    name: 'id',
                    type: 'string'
                },
                {
                    name: 'name',
                    type: 'string'
                }
            ]
        }, cfg));
    }
});
