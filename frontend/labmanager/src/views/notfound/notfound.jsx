import React,{Component} from 'react';

export default class NotFound extends Component{
  componentWillMount(){
    this.props.history.replace('/error');
  }

  render(){
    return (
      <div>404</div>
    )
  }
}