import {URLSearchParams} from "https://jslib.k6.io/url/1.0.0/index.js";
import {randomIntBetween, randomString, uuidv4} from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';
import http from "k6/http";
import {check} from "k6";

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
    const hostname = `${__ENV.HOSTNAME}`
    const params = {
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    };

    const itemUid = uuidv4();
    const body = {"text": `Hello, ${randomString(8)}`, "uid": itemUid}
    let response = http.post(`http://${hostname}/backend/api/v1/public/items`, JSON.stringify(body), params);
    check(response, {
        "status is OK": (r) => r.status === 201
    });

    response = http.get(`http://${hostname}/backend/api/v1/public/items`, params);
    check(response, {
        "status is OK": (r) => r.status === 200,
        "content is present": (r) => !!r.body,
    });

    if (randomIntBetween(1, 10) !== 10) {
        response = http.del(`http://${hostname}/backend/api/v1/public/items/${itemUid}`, null, params);
        check(response, {
            "status is NO_CONTENT": (r) => r.status === 204
        });
    }
};