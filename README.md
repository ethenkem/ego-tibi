# Ego Tibi

**A self-contained, in-house IT helpdesk system built on Spring Boot microservices and RabbitMQ. Designed so any company's IT team can deploy it with a single file and a single command.**

> *Ego tibi*: Latin for "I, to you." A system that serves the people who raise their hand for help.

No cloning a repo. No building from source. No installing Java or Maven. Just one `docker-compose.yml`, pointed at pre-built images on Docker Hub, and the entire helpdesk system (ticket intake, automatic team routing, and SLA-based escalation) is running.

## What this project actually is

Ego Tibi is a ticketing system for internal IT support, built around one core idea: **when a ticket isn't acknowledged in time, it should escalate itself automatically, without anyone writing a polling loop or a cron job to check on it.**

It's composed of four small, independent services that communicate primarily through RabbitMQ events rather than calling each other directly. Each piece can be understood, deployed, and even replaced on its own, the way real production microservice systems are built.

This is also an open-source reference project: a clear, working example of RabbitMQ's **TTL + Dead Letter Exchange (DLX)** pattern used for real SLA escalation, rather than the more commonly demonstrated and much simpler basic publish/subscribe pattern.

## What it does, concretely

1. Someone submits an IT ticket: title, description, category (Hardware / Software / Network / Access / Other), and priority (Critical / High / Medium / Low).
2. The system automatically assigns it to the right first-line team, based on category.
3. A countdown starts, sized to the ticket's priority (5 to 60 minutes).
4. If an agent acknowledges the ticket in time, nothing else happens; the ticket proceeds normally.
5. If nobody acknowledges it in time, the ticket **automatically escalates** to the next tier (e.g. team lead, then IT manager), and the countdown restarts for the new tier.

No external dependencies, no cloud services, no third-party APIs. Everything required to run it is self-contained, designed specifically to run **on-premises, inside a company's own infrastructure**, behind their own firewall, with nothing reaching out to the public internet.

## Who this is for / where it should be installed

This is meant to run on a **server the IT team already controls**, not a developer's laptop and not a public cloud-facing endpoint. Realistic deployment targets:

- An internal company server or VM, on the company's own network
- A self-hosted homelab or on-prem rack, for smaller organizations
- An internal Docker host or lightweight Kubernetes node, for IT teams with existing container infrastructure

It is **not**, in its current form, meant to be exposed directly to the public internet, since there's no authentication layer yet (see Roadmap). It's built for internal, trusted-network use: an IT helpdesk that only people inside the company can reach, the same way most internal ticketing tools work today.

## Architecture

```
                         ┌──────────────┐
   Browser (employees,    │   Web UI       │  the only thing end users ever touch
   IT agents) ───────────►│ (port 8080)    │
                         └──────┬───────┘
                                │ REST
                                ▼
                         ┌──────────────┐
                         │ Ticket Service │  (owns the database, the source of truth)
                         └──────┬───────┘
                                  │ publishes
                                  ▼
                      ┌────────────────────────┐
                      │   RabbitMQ Topic Exchange │
                      │       "egotibi.events"     │
                      └────────────┬───────────────┘
                ┌──────────────────┼──────────────────────┐
                ▼                  ▼                       ▼
       ┌──────────────────┐ ┌───────────────────┐ ┌───────────────────┐
       │ Routing Service    │ │ Escalation Service │ │ Notification Svc  │
       │ (category rules)    │ │ (TTL + DLX timers)  │ │ (pure consumer)    │
       └──────────────────┘ └───────────────────┘ └───────────────────┘
```

| Service | Responsibility | Database |
|---|---|---|
| **Web UI** | What employees and IT agents actually use: submit tickets, view status, acknowledge/resolve. Talks only to Ticket Service. | None |
| **Ticket Service** | Source of truth for tickets. REST API for create/acknowledge/resolve. Publishes lifecycle events. | PostgreSQL |
| **Routing Service** | Applies category to team rules. Writes assignment back to Ticket Service. | None (stateless) |
| **Escalation Service** | Arms SLA timers via RabbitMQ TTL/DLX. Escalates unacknowledged tickets automatically. | None (state lives in the broker) |
| **Notification Service** | Reacts to routing/escalation/resolution events, sends notifications. | None (stateless) |

Every cross-service interaction is either a RabbitMQ event ("FYI, this happened, react if you care") or a direct REST call ("I need this written and confirmed before I continue"). No service ever touches another service's database.

## Installation

### Requirements

Only **Docker** and **Docker Compose**, nothing else. No Java, no Maven, no manual builds. The images are pre-built and published to Docker Hub.

### Steps

```bash
# 1. Get the compose file (no repo clone needed)
curl -O https://raw.githubusercontent.com/<your-username>/ego-tibi/main/docker-compose.yml

# 2. Get the environment template and fill in your own values
curl -O https://raw.githubusercontent.com/<your-username>/ego-tibi/main/.env.example
cp .env.example .env
nano .env   # set POSTGRES_PASSWORD and any other secrets

# 3. Run it
docker compose up -d
```

That's the entire installation. Docker Compose pulls each service's image directly from Docker Hub (`docker.io/<your-dockerhub-username>/ego-tibi-ticket-service`, etc.), starts RabbitMQ and PostgreSQL alongside them, and wires up the network between all of them automatically.

### Verifying it's running

```bash
docker compose ps
```

All containers should show `Up`.

- **Ego Tibi (the helpdesk app)**: `http://localhost:8080`. This is the address your IT team and employees actually use day to day.
- **RabbitMQ management UI**: `http://localhost:15672`. Optional, for watching exchanges, queues, and message flow live; useful for the team running the system, not for end users.

### Submitting a ticket

Open `http://localhost:8080` in a browser and submit a ticket through the form: title, description, category, and priority. The UI talks to Ticket Service behind the scenes; nobody needs to know or care that an API is involved at all.

Once submitted, you'll see the ticket get assigned to a team automatically, and if it sits unacknowledged past its SLA window, it escalates on its own.

## Routing & SLA rules (v1)

| Category | Tier 1 | Tier 2 | Tier 3 |
|---|---|---|---|
| HARDWARE | Hardware Support | Hardware Team Lead | IT Manager |
| SOFTWARE | Software Support | Software Team Lead | IT Manager |
| NETWORK | Network Ops | Network Team Lead | IT Manager |
| ACCESS | Security Team | Security Lead | IT Manager |
| OTHER | General Support | IT Manager | (none) |

| Priority | SLA window (time to acknowledge) |
|---|---|
| CRITICAL | 5 minutes |
| HIGH | 15 minutes |
| MEDIUM | 30 minutes |
| LOW | 60 minutes |

These are intentionally simple and hardcoded for v1, kept that way deliberately so the event-driven escalation pattern stays easy to follow before adding configurability on top.

## For developers: building from source instead

The Docker Hub images are the intended way to run this for real use. Every service's source code lives directly in this repo, under `services/`. If you want to modify routing rules, SLA windows, notification behavior, or anything else, clone the repo and build locally instead of pulling pre-built images:

```bash
git clone https://github.com/<your-username>/ego-tibi.git
cd ego-tibi
docker compose -f docker-compose.dev.yml up --build
```

`docker-compose.dev.yml` builds each service's image from the source in `services/` rather than pulling from Docker Hub. `docker-compose.yml` (the one most people use) skips all of that and just pulls the already-built images straight from Docker Hub.

## Project structure

```
ego-tibi/
├── docker-compose.yml           (production: pulls pre-built images from Docker Hub)
├── docker-compose.dev.yml       (development: builds images locally from source)
├── .env.example
├── services/
│   ├── web-ui/
│   ├── ticket-service/
│   ├── routing-service/
│   ├── escalation-service/
│   └── notification-service/
└── README.md
```

## Why this project exists

This started as a hands-on way to learn RabbitMQ properly, specifically the TTL/dead-letter-exchange pattern, which is one of the more elegant tricks the broker offers and one that's rarely demonstrated end-to-end in tutorials that just show basic publish/subscribe. It's open source in case it's useful as a learning reference, a starting point for an actual internal helpdesk tool, or something to fork and improve.

Contributions, issues, and forks are welcome.

## Roadmap

- [ ] Web UI (in progress): ticket submission, status view, agent acknowledge/resolve actions
- [ ] Authentication/authorization on the API (required before any internet-facing deployment)
- [ ] Configurable routing rules (DB-backed instead of hardcoded)
- [ ] Admin dashboard for managing tickets across all teams
- [ ] Real notification delivery (SMTP/Slack webhook) instead of console logging
- [ ] Notification history persistence
- [ ] Metrics dashboard (tickets by status, average time-to-acknowledge, SLA breach rate)
- [ ] Outbox pattern for reliable event publishing (closing the dual-write gap between DB writes and RabbitMQ publishes)

## License

MIT. See `LICENSE`.