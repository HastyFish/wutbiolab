import React,{Component} from 'react';

import {Link} from 'react-router-dom';
 
export default class Error extends Component {

	render(){
		return (
			<div id='page-wrapper'>
				<div className="row" style={{marginTop:'30px'}}>
					<div className="col-md-12">
						<span>页面被狗狗叼走啦～～～</span>
						<Link to='/login'>点我返回首页</Link>
					</div>
				</div>
 
			</div>
		)
	}
}
