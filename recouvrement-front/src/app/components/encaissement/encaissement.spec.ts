import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Encaissement } from './encaissement';

describe('Encaissement', () => {
  let component: Encaissement;
  let fixture: ComponentFixture<Encaissement>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Encaissement]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Encaissement);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
