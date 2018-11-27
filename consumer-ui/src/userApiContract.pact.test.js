/**
 * @jest-environment node
 */

import { Matchers, Pact } from "@pact-foundation/pact";
import path from "path";
import {fetchUserData} from "./client/userDataClient";

const MOCK_SERVER_PORT = 4711;
jasmine.DEFAULT_TIMEOUT_INTERVAL = 10000;

describe("fetch user data", () => {

    // create mock server
    const pact = new Pact({
        consumer: "user-data-ui",
        provider: "user-data-provider",
        port: MOCK_SERVER_PORT,
        log: path.resolve(process.cwd(), "dist/logs", "pact.log"),
        dir: path.resolve(process.cwd(), "dist/pacts"),
        logLevel: "INFO",
        spec: 1,
        cors: true
    });

    // define contract
    describe("can fetch User Data", () => {
        beforeEach((done) => pact
                .setup()
                .then(() => {
                    // define expected response
                    const expectedResponse = {
                        firstName: Matchers.somethingLike("aValidFirstName"),
                        lastName: Matchers.somethingLike("aValidLastName"),
                        age: Matchers.integer(100)
                    };

                    // define request
                    return pact.addInteraction({
                        state: "some user available",

                        uponReceiving: "a user request",

                        withRequest: {
                            method: "GET",
                            path: "/user",

                            headers: {
                                Accept: Matchers.term({
                                    matcher: "application/json",
                                    generate: "application/json"
                                })
                            },
                        },

                        willRespondWith: {
                            status: 200,
                            headers: {"Content-Type": "application/json; charset=utf-8"},
                            body: Matchers.somethingLike(expectedResponse)
                        }
                    });
                })
                .then(() => done())
        );

        // validate contract definition
        it("can load user data", () => {

            expect.assertions(3);

            const url = `http://localhost:${MOCK_SERVER_PORT}/user`;

            const promise = fetchUserData(url);

            return promise.then(response => {
                expect(response.status).toBe(200);
                expect(response.headers['content-type']).toBe("application/json; charset=utf-8");
                expect(response.data).toMatchObject({
                    firstName: "aValidFirstName",
                    lastName: "aValidLastName",
                    age: 100
                });
            });
        });
    });

    // create contract / pact file
    afterAll(() => pact.finalize());
});
