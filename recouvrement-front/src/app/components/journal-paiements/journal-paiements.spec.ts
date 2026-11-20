import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JournalPaiements } from './journal-paiements';

describe('JournalPaiements', () => {
  let component: JournalPaiements;
  let fixture: ComponentFixture<JournalPaiements>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JournalPaiements]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JournalPaiements);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
