import React, { Component } from 'react';
import {getLabId} from '@/api'


class ListInfo extends Component {
    constructor(props) {
        super(props);
        let infoId = props.infoId;
        debugger;
        this.state = {
            infoId,
            dataList:{},
            next:{},
            pre:{}
          }
    }
   componentDidMount(){
        this.getLab(this.state.infoId);
    }
        //获取表格数据
        getLab = async(id)=>{
            let data = await getLabId(id);
            if(data.result   ){
                this.setState({
                    dataList : data.result.labDetail,
                    next : data.result.next,
                    pre : data.result.pre,
                    infoId:id
                })
            }
        }
    render() { 
        let {dataList,next,pre} = this.state;
        return ( 
            <div>
                <div className="title-name">
                    {dataList.title}
                </div>
                <p dangerouslySetInnerHTML={{__html: dataList.context}}></p>
            </div>
         );
    }
}
 
export default ListInfo;