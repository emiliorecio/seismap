/*
 * File: LocationEventStore.js
 * Date: Mon Apr 11 2011 02:10:00 GMT-0300 (Argentina Standard Time)
 * 
 * This file was generated by Ext Designer version 1.1.2.
 * http://www.sencha.com/products/designer/
 *
 * This file will be auto-generated each and everytime you export.
 *
 * Do NOT hand edit this file.
 */

LocationEventStore = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        LocationEventStore.superclass.constructor.call(this, Ext.apply({
            storeId: 'locationEventStore',
            fields: [
                {
                    name: 'id',
                    type: 'int'
                },
                {
                    name: 'information',
                    type: 'string'
                },
                {
                    name: 'date',
                    type: 'date'
                },
                {
                    name: 'magnitude',
                    type: 'float'
                },
                {
                    name: 'rankmagnitude',
                    type: 'float'
                }
            ]
        }, cfg));
    }
});
