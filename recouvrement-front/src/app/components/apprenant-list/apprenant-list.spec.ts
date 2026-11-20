import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprenantList } from './apprenant-list';

describe('ApprenantList', () => {
  let component: ApprenantList;
  let fixture: ComponentFixture<ApprenantList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ApprenantList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApprenantList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
