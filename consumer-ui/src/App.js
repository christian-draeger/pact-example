import React, {Component} from 'react';
import {fetchUserData} from "./client/userDataClient";
import {getEndpoint} from "./utils/environment";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: {}
        };
    }

    componentWillMount() {
        fetchUserData(getEndpoint())
            .then(({data}) => this.setState({data}));
    }

    render() {
        return (
            <div>
                <div>{this.state.data.firstName}</div>
                <div>{this.state.data.lastName}</div>
                <div>{this.state.data.age}</div>
            </div>
        );
    }
}

export default App;
