import React, { Component } from 'react'
import './index.less'
class LeftSide extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            titleinfo : props.titleinfo,
            navList : props.navList || [{id:1,name:"xx西傻大姐"},{id:2,name:"xx西傻大姐"},{id:3,name:"xx西傻大姐"},{id:4,name:"xx西傻大姐"}],
            activeNav:0
         }
    }
    changeNav=(item,index)=>{
        this.setState({
            activeNav: index
        })
    }
    render() { 
        const {titleinfo,navList,activeNav} = this.state;
        return ( 
            <div className="leftSide">
                <div className = "leftSide-header">
                    {titleinfo}
                </div>
                <ul className = "leftSide-container">
                    {
                        !!navList.length && navList.map((item,index)=>{
                            return(
                                <li className={activeNav === index ? "active" : null} key={index} onClick={this.changeNav.bind(this,item,index)}>{item.name}</li>
                            )
                        })
                    }
                </ul>
            </div>
         );
    }
}
 
export default LeftSide;