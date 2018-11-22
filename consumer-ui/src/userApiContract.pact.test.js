/**
 * @jest-environment node
 */

import { Matchers, Pact } from "@pact-foundation/pact";

it('contract test', () => {
    expect(2 + 2).toBe(4);
});