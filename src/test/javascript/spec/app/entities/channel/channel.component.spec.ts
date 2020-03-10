import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BroadcastTestModule } from '../../../test.module';
import { ChannelComponent } from 'app/entities/channel/channel.component';
import { ChannelService } from 'app/entities/channel/channel.service';
import { Channel } from 'app/shared/model/channel.model';

describe('Component Tests', () => {
  describe('Channel Management Component', () => {
    let comp: ChannelComponent;
    let fixture: ComponentFixture<ChannelComponent>;
    let service: ChannelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BroadcastTestModule],
        declarations: [ChannelComponent]
      })
        .overrideTemplate(ChannelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChannelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChannelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Channel(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.channels && comp.channels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
