import React,{Component} from 'react';
import {Switch, Route, Redirect} from 'react-router-dom';

import Article from './article/article';
import EditArticle from './article/add-edit-article';
import Derection from './deriection/deriection'
import Academic from './academic/academic';
import EditAcademic from './academic/add-edit-academic';

import './science.less'


class Science extends Component {

  render(){
    return (
      <div className='lab'>
        <Switch>
          <Route path='/science' exact component={Derection} />
          <Route path='/science/article' exact component={Article} />
          <Route path='/science/article/edit' component={EditArticle} />
          <Route path='/science/academic' exact component={Academic} />
          <Route path='/science/academic/edit' component={EditAcademic} />
          <Redirect to='/science' />
        </Switch>
      </div>
    )
  }
}

export default Science