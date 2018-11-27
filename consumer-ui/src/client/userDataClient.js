import axios from "axios";

export const fetchUserData = (url) => {
    return axios.get(url)
};