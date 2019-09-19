import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Admin from "@views/admin/admin"

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Switch>{/*只匹配其中一个路由*/}
          <Route path='/' component={Admin}></Route>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
