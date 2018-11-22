import {getEndpoint} from "../utils/environment";

export const fetchUserData = () => {
    return fetch(getEndpoint(), {
        method: 'GET',
        mode: "cors"
    })
};