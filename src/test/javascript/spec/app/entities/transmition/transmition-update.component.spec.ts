import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BroadcastTestModule } from '../../../test.module';
import { TransmitionUpdateComponent } from 'app/entities/transmition/transmition-update.component';
import { TransmitionService } from 'app/entities/transmition/transmition.service';
import { Transmition } from 'app/shared/model/transmition.model';

describe('Component Tests', () => {
  describe('Transmition Management Update Component', () => {
    let comp: TransmitionUpdateComponent;
    let fixture: ComponentFixture<TransmitionUpdateComponent>;
    let service: TransmitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BroadcastTestModule],
        declarations: [TransmitionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransmitionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransmitionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransmitionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Transmition(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Transmition();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
