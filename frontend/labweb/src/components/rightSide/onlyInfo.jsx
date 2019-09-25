import React from 'react';
import { Breadcrumb } from 'antd';
import './index.less'
import {getLabOneById} from '@/api'
import  {NavTitle} from "@utils/titleConfig"

class OnlyInfo extends React.PureComponent {
    constructor(props) {
        super(props);
        let pathnameList = this.props.location.pathname.split("/");
        let navId = Number(pathnameList[pathnameList.length - 1]);
        this.state = { 
            titleName : "",
            dataList:{},
            navId,
            titleinfo:"",
            navName:""
         }
    }
    // //动态获取父级传来的值再做渲染
    componentWillReceiveProps(nextProps) {
        if (nextProps.location.state) {
                this.setState({
                    navId:nextProps.location.state.navId,
                    titleinfo :nextProps.location.state.titleinfo,
                },()=>{ this.getOne()})
            }
        
        
    }
    componentDidMount(){
        this.getOne();
    }
    //获取一段文字
     getOne = async()=>{
        if(this.props.location.state){
            this.setState({
                titleinfo :this.props.location.state.titleinfo,
            })
        }
        let data = await getLabOneById(this.state.navId);
        if(data.result){
            this.setState({
                dataList : data.result,
                navName:data.result.category
            })
        }else{
            this.setState({
                dataList : {},
                navName:""
            })
        }
    }
    render() { 
        let {dataList,titleinfo,navName} = this.state;
        return ( 
            <div className="right-container">
                <Breadcrumb separator=">">
                    <Breadcrumb.Item>Home</Breadcrumb.Item>
                    <Breadcrumb.Item>{titleinfo||NavTitle[1].en}</Breadcrumb.Item>
                    <Breadcrumb.Item >{navName}</Breadcrumb.Item>
                </Breadcrumb>
                <div className="title-name">
                    {/* {dataList.title} */}
                </div>
                <p dangerouslySetInnerHTML={{__html: dataList.context}}></p>
            </div>
         );
    }
}
 
export default OnlyInfo