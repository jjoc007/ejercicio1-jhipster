import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BroadcastTestModule } from '../../../test.module';
import { DayComponent } from 'app/entities/day/day.component';
import { DayService } from 'app/entities/day/day.service';
import { Day } from 'app/shared/model/day.model';

describe('Component Tests', () => {
  describe('Day Management Component', () => {
    let comp: DayComponent;
    let fixture: ComponentFixture<DayComponent>;
    let service: DayService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BroadcastTestModule],
        declarations: [DayComponent]
      })
        .overrideTemplate(DayComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DayComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DayService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Day(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.days && comp.days[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
