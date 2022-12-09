import {URLSearchParams} from "https://jslib.k6.io/url/1.0.0/index.js";
import http from "k6/http";

export const options = {
    stages: [
        {duration: "20s", target: 10},
        {duration: "30s", target: 20},
        {duration: "1m30s", target: 50},
        {duration: "20s", target: 10},
    ],
};

export function setup() {
    const body = new URLSearchParams({
        "scope": "openid",
        "grant_type": "password",
        "username": `${__ENV.USERNAME}`,
        "password": `${__ENV.PASSWORD}`,
        "client_id": `${__ENV.CLIENT_ID}`,
        "client_secret": `${__ENV.CLIENT_SECRET}`
    })
    const params = {
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    }

    const url = "https://romanowalex.eu.auth0.com/oauth/token"
    const result = http.post(url, body.toString(), params)
    return result.json().id_token
}

export default function (token) {
    const params = {
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    };


};