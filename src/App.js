import React from 'react';
import Component from './core/MyComponent';
import {
  HashRouter,
  Switch,
  Route
} from 'react-router-dom';
import Style from './App.module.css';
import WelcomeCmp from './app/welcome';
import AppCmp from './app/app';
/* hahahahhahahahha */
class App extends Component {
  render() {
    return (
      <div className={Style.body} align="center">
      <HashRouter>
        <Switch>
          <Route exact path='/' component={WelcomeCmp}/>
          <Route path='/app' component={AppCmp}/>
        </Switch>
      </HashRouter>
      </div>
    );
  }
}

export default App;