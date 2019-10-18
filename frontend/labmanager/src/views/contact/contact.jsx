import React,{Component} from 'react';
import {Switch, Route, Redirect} from 'react-router-dom';

import contactUs from './contactUs/contactUs';
import './contact.less'



class contact extends Component {

  render(){
    return (
      <div className='lab'>
        <Switch>
          <Route path='/contact' exact component={contactUs} />
          <Redirect to='/contact' />
        </Switch>
      </div>
    )
  }
}

export default contact