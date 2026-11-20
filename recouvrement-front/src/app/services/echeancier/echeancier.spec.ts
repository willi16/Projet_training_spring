import { TestBed } from '@angular/core/testing';

import { Echeancier } from './echeancier';

describe('Echeancier', () => {
  let service: Echeancier;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Echeancier);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
