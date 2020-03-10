import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BroadcastTestModule } from '../../../test.module';
import { TransmitionComponent } from 'app/entities/transmition/transmition.component';
import { TransmitionService } from 'app/entities/transmition/transmition.service';
import { Transmition } from 'app/shared/model/transmition.model';

describe('Component Tests', () => {
  describe('Transmition Management Component', () => {
    let comp: TransmitionComponent;
    let fixture: ComponentFixture<TransmitionComponent>;
    let service: TransmitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BroadcastTestModule],
        declarations: [TransmitionComponent]
      })
        .overrideTemplate(TransmitionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransmitionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransmitionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Transmition(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transmitions && comp.transmitions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
