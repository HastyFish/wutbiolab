import React,{Component} from 'react';
import storageUtils from '@/utils/storageUtils';
import {Switch, Route, Redirect} from 'react-router-dom';

import Article from './article/article';
import EditArticle from './article/add-edit-article';
import Academic from './academic/academic';
import EditAcademic from './academic/add-edit-academic';

import './science.less'


class Science extends Component {


  componentWillMount(){
    const user = storageUtils.getUser() || {};
    if(!user || !user.username){
      //自动跳转到登陆
      this.props.history.replace('/login')
    }
  }


  render(){
    return (
      <div className='lab'>
        <Switch>
          <Route path='/science' exact component={Article} />
          <Route path='/science/edit' component={EditArticle} />
          <Route path='/science/academic' exact component={Academic} />
          <Route path='/science/academic/edit' component={EditAcademic} />
          <Redirect to='/science' />
        </Switch>
      </div>
    )
  }
}

export default Science