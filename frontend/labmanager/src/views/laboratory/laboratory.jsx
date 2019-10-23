import React,{Component} from 'react';
import {Switch, Route, Redirect} from 'react-router-dom';

import LabDscription from './labDescription/labDescription';
// import Deriection from './deriection/deriection';
import Team from './team/team';
import PersonEdit from './team/personedit/person-edit';
import Graduates from './graduates/graduates';
import GraduatesEdit from './graduates/graduate-edit';
import './laboratory.less'



class Laboratory extends Component {

  render(){
    return (
      <div className='lab'>
        <Switch>
          <Route path='/laboratory' exact component={LabDscription} />
          {/* <Route path='/laboratory/derection' component={Deriection} /> */}
          <Route path='/laboratory/team' exact component={Team} />
          <Route path='/laboratory/team/edit' component={PersonEdit} />
          <Route path='/laboratory/graduates' exact component={Graduates} />
          <Route path='/laboratory/graduates/edit' component={GraduatesEdit} />
          <Redirect to='/laboratory' />
        </Switch>
      </div>
    )
  }
}

export default Laboratory