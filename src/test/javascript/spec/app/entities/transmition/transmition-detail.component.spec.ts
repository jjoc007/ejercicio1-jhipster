import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BroadcastTestModule } from '../../../test.module';
import { TransmitionDetailComponent } from 'app/entities/transmition/transmition-detail.component';
import { Transmition } from 'app/shared/model/transmition.model';

describe('Component Tests', () => {
  describe('Transmition Management Detail Component', () => {
    let comp: TransmitionDetailComponent;
    let fixture: ComponentFixture<TransmitionDetailComponent>;
    const route = ({ data: of({ transmition: new Transmition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BroadcastTestModule],
        declarations: [TransmitionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransmitionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransmitionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load transmition on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transmition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
