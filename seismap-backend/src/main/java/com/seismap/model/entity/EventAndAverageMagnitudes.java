package com.seismap.model.entity;

import jakarta.persistence.*;

/**
 * Read-only entity mapped to the eventandaveragemagnitudes DB view.
 * Provides pre-averaged magnitude values per event.
 */
@Entity
@Table(name = "eventandaveragemagnitudes")
@org.hibernate.annotations.Immutable
public class EventAndAverageMagnitudes extends EventInfo {

    @Id
    private Long id;

    @Column(name = "rank_magnitude")
    private Float rankMagnitude;

    @Column(name = "mb_magnitude")
    private Float mbMagnitude;

    @Column(name = "mblg_magnitude")
    private Float mblgMagnitude;

    @Column(name = "mc_magnitude")
    private Float mcMagnitude;

    @Column(name = "ml_magnitude")
    private Float mlMagnitude;

    @Column(name = "ms_magnitude")
    private Float msMagnitude;

    @Column(name = "mw_magnitude")
    private Float mwMagnitude;

    protected EventAndAverageMagnitudes() {
    }

    public Long getId() {
        return id;
    }

    public Float getRankMagnitude() {
        return rankMagnitude;
    }

    public Float getMbMagnitude() {
        return mbMagnitude;
    }

    public Float getMblgMagnitude() {
        return mblgMagnitude;
    }

    public Float getMcMagnitude() {
        return mcMagnitude;
    }

    public Float getMlMagnitude() {
        return mlMagnitude;
    }

    public Float getMsMagnitude() {
        return msMagnitude;
    }

    public Float getMwMagnitude() {
        return mwMagnitude;
    }
}
