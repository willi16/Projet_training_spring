import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EtatRetards } from './etat-retards';

describe('EtatRetards', () => {
  let component: EtatRetards;
  let fixture: ComponentFixture<EtatRetards>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EtatRetards]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EtatRetards);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
