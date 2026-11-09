import http from 'k6/http';
import { check } from 'k6';

export const options = {
    vus: 1,
    iterations: 10,
    thresholds: {
        http_req_duration: ['p(95)<500'],
    },
};

export default function () {
    const response = http.get('http://localhost:8080/readings');

    check(response, {
        'GET /readings retorna status 200': (res) => res.status === 200,
    });
}
