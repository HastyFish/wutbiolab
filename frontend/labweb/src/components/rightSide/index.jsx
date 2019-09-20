import React, { Component } from 'react';

class RightSide extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    componentWillReceiveProps(nextProps) {
        let navId = null;
        if(!nextProps.location.state){
            navId = 1
        }else{
            navId = nextProps.location.state.navId
        }
        this.props.history.push(`/introduction/${navId}`)
        }
    render() { 
        return ( 
            <div>
                RightSide
            </div>
         );
    }
}
 
export default RightSide;