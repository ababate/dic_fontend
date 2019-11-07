import React, { Component } from 'react';
import * as Utils from './Utils';
export default class MyComponent extends Component {
  constructor() {
    super();
    this.Utils = Utils;
    // this.MyEvent = Utils.MyEvent;
    this.on = function(eventName, handler) {
      Utils.MyEvent.on(eventName, handler, this.constructor.name);
    }
    this.emit =function(eventName) {
      const handlerArgs = Array.prototype.slice.call(arguments,0);
      Utils.MyEvent.emit(...handlerArgs);
    } 
    this.off = function(eventName) {
      Utils.MyEvent.off(eventName, this.constructor.name)
    }
    this.showEvent = function(){
      Utils.MyEvent.show();
    } 
    this.Cache = Utils.Cache;
  }
}