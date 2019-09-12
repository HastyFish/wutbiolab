import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Login from '@/views/login/login';
import Admin from '@/views/admin/admin';
import Error from '@/views/error/error';
import '@/assets/style/common.less';

function App() {
  return (
    <div className="App" style={{height:"100%",minWidth:"1200px"}}>
      <BrowserRouter>
        <Switch>{/*只匹配其中一个路由*/}
          <Route path='/login' component={Login}></Route>
          <Route path='/error' component={Error}></Route>
          <Route path='/' component={Admin}></Route>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
