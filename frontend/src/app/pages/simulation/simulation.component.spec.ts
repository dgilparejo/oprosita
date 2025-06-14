import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimulationComponent } from './simulation.component';

describe('SimulationComponent', () => {
  let component: SimulationComponent;
  let fixture: ComponentFixture<SimulationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SimulationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SimulationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
//generame el test de este componente, que es un componente de angular, y que se llama simulation.component.ts
