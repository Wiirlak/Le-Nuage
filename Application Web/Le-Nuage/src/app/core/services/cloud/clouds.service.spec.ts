import { TestBed } from '@angular/core/testing';

import { CloudsService } from './clouds.service';

describe('CloudsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CloudsService = TestBed.get(CloudsService);
    expect(service).toBeTruthy();
  });
});
