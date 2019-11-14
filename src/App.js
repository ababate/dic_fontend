import React from 'react';
import Component from './core/MyComponent';
import { HashRouter, Switch, Route } from 'react-router-dom';
import Style from './App.module.css';
import WelcomeCmp from './app/welcome';
import AppCmp from './app/app';
class App extends Component {
  render() {
    return (
      <HashRouter>
        <Switch>
          <Route exact path='/' component={WelcomeCmp}/>
          <Route path='/app' component={AppCmp}/>
        </Switch>
      </HashRouter>
    );
  }
}

export default App;
