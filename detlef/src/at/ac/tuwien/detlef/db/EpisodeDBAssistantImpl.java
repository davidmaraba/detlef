package at.ac.tuwien.detlef.db;

import java.util.List;

import android.content.Context;
import android.util.Log;
import at.ac.tuwien.detlef.domain.Episode;
import at.ac.tuwien.detlef.domain.Episode.State;
import at.ac.tuwien.detlef.domain.Podcast;

import com.dragontek.mygpoclient.api.EpisodeAction;
import com.dragontek.mygpoclient.api.EpisodeActionChanges;
import com.dragontek.mygpoclient.feeds.IFeed;
import com.dragontek.mygpoclient.feeds.IFeed.IEpisode;

public class EpisodeDBAssistantImpl implements EpisodeDBAssistant {

    private static final String TAG = EpisodeDBAssistantImpl.class.getName();

    @Override
    public List<Episode> getEpisodes(Context context, Podcast podcast) {
        EpisodeDAO dao = EpisodeDAOImpl.i(context);
        return dao.getEpisodes(podcast);
    }

    @Override
    public List<Episode> getAllEpisodes(Context context) {
        EpisodeDAO dao = EpisodeDAOImpl.i(context);
        return dao.getAllEpisodes();
    }

    @Override
    public void applyActionChanges(Context context, Podcast podcast,
            EpisodeActionChanges changes) {
        for (EpisodeAction action : changes.actions) {
            // do something with it

        }

    }

    @Override
    public void upsertAndDeleteEpisodes(Context context, Podcast p, IFeed feed) {
        try {
            EpisodeDAO dao = EpisodeDAOImpl.i(context);
            for (IEpisode ep : feed.getEpisodes()) {
                Episode newEp = new Episode();
                newEp.setAuthor(ep.getAuthor());
                newEp.setDescription(ep.getDescription());
                newEp.setFileSize(String.valueOf(ep.getEnclosure()
                        .getFilesize()));
                newEp.setGuid(ep.getGuid());
                newEp.setLink(ep.getLink());
                newEp.setMimetype(ep.getEnclosure().getMimetype());
                newEp.setPodcast(p);
                newEp.setReleased(ep.getReleased());
                newEp.setState(State.NEW); // TODO: which state?
                newEp.setTitle(ep.getTitle());
                newEp.setUrl(ep.getEnclosure().getUrl());
                dao.insertEpisode(newEp);
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public Episode getEpisodeById(Context context, int id) {
        EpisodeDAO dao = EpisodeDAOImpl.i(context);
        return dao.getEpisode(id);
    }

}
