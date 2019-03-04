import { TestBed } from '@angular/core/testing';

import { ApiAppleService } from './api-apple.service';

describe('ApiAppleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ApiAppleService = TestBed.get(ApiAppleService);
    expect(service).toBeTruthy();
  });
});
